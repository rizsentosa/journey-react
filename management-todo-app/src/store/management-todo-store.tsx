import { useReducer } from "react";
import type { ManagementTodoState, ManagementTodoAction } from '@/types/todo.type';
import { ManagementTodoStore } from './data-context';

function generateTimeBasedId(): string {
  const timestamp = Date.now().toString(36); // Ubah timestamp ke base36 agar lebih pendek
  const randomPart = Math.random().toString(36).substring(2, 9); // 7 karakter random

  return `${timestamp}-${randomPart}`;
}


function managementTodoReducer(state: ManagementTodoState, action: ManagementTodoAction): ManagementTodoState {

  if (action.type === 'start_add_projects') {
    return {
      ...state,
      selectedProjectId: null
    }
  }

  if (action.type === 'cancel_add_projects') {
    return {
      ...state,
      selectedProjectId: undefined
    }
  }

  if (action.type === 'selected_project') {
    return {
      ...state,
      selectedProjectId: action.payload.id
    }
  }

  if (action.type === 'add_projects') {
    const newProject = {
      id: generateTimeBasedId(),
      title: action.payload.title!,
      description: action.payload.description!,
      createdAt: action.payload.createdAt!,
    }

    return {
      ...state,
      selectedProjectId: undefined,
      projects: [...state.projects, newProject]
    };
  }

  if (action.type === 'delete_projects') {
    const existsDataAfterDelete = state.projects.filter(p => p.id != action.payload.id);
    return {
      ...state,
      selectedProjectId: undefined,
      projects: existsDataAfterDelete
    }
  }


  if (action.type === 'add_task_to_projects') {
    const newTask = {
      id: generateTimeBasedId(),
      title: action.payload.title!,
      projectId: state.selectedProjectId,
    }
    return {
      ...state,
      tasks: [newTask, ...state.tasks]
    };
  }

  if (action.type === 'delete_task_to_projects') {
    const tasks = state.tasks.filter(t => t.id != action.payload.id);
    return {
      ...state,
      tasks
    }
  }

  return state;
}

export default function ManagementTodoStoreProvider({ children }: { children: React.ReactNode }) {

  const [managementTodoState, managementTodoDispacth] = useReducer(managementTodoReducer, {
    selectedProjectId: undefined,
    projects: [],
    tasks: []
  });

  // const [projectStates, setProjectStates] = useState<{
  //   selectedProjectId: TSelectedProjectId,
  //   projects: TTodo[],
  //   tasks: TTaskTodo[]
  // }
  // >({
  //   selectedProjectId: undefined,
  //   projects: [],
  //   tasks: []
  // });

  // sudah dimasukkan ke reducer
  const handleAddTaskToProjects = (input: string) => {
    // setProjectStates((prevState) => {
    //   const newTask = {
    //     title: input,
    //     projectId: prevState.selectedProjectId,
    //     id: generateTimeBasedId()
    //   }
    //   return {
    //     ...prevState,
    //     tasks: [newTask, ...prevState.tasks]
    //   };
    // })

    managementTodoDispacth({
      type: 'add_task_to_projects',
      payload: { title: input }
    })
  }


  // sudah dimasukkan ke reducer
  const handleDeleteTaskToProjects = (id: string) => {
    // const tasks = projectStates.tasks.filter(t => t.id != id);
    // setProjectStates(prevState => {
    //   return {
    //     ...prevState,
    //     tasks
    //   }
    // })

    managementTodoDispacth({
      type: 'delete_task_to_projects',
      payload: { id }
    })
  }

  /**
   * update state selected project todo
   * @param id 
   */
  const handleSelectedProject = (id: string) => {
    // setProjectStates((prevState) => {
    //   return {
    //     ...prevState,
    //     selectedProjectId: id
    //   }
    // });

    managementTodoDispacth({
      type: 'selected_project',
      payload: {
        id
      }
    })
  }

  /**
   * initial select project id to null 
   */
  const handleStartAddProject = () => {
    // setProjectStates((prevState) => {
    //   return {
    //     ...prevState,
    //     selectedProjectId: null
    //   }
    // });

    managementTodoDispacth({
      type: 'start_add_projects', payload: {}
    })
  }

  const handleCancelAddProject = () => {
    // console.log("clicked...");
    // setProjectStates((prevState) => {
    //   return {
    //     ...prevState,
    //     selectedProjectId: undefined
    //   }
    // });

    managementTodoDispacth({
      type: 'cancel_add_projects', payload: {}
    })
  }

  /**
   * manipulate data to projectStates
   * @param projectData 
   */

  // sudah dimasukkan ke reducer
  const handleAddProject = (projectData: { title: string, description: string, createdAt: string }) => {
    console.log('test....');
    const newProject = {
      ...projectData,
      id: generateTimeBasedId()
    }

    // setProjectStates((prevState) => {
    //   return {
    //     ...prevState,
    //     selectedProjectId: undefined,
    //     projects: [...prevState.projects, newProject]
    //   };
    // })

    managementTodoDispacth({
      type: 'add_projects',
      payload: newProject
    })
  }

  // sudah dimasukkan ke reducer
  const handleDeleteProject = (id: string) => {
    // const existsDataAfterDelete = projectStates.projects.filter(p => p.id != id);
    // setProjectStates((prevState) => {
    //   return {
    //     ...prevState,
    //     selectedProjectId: undefined,
    //     projects: existsDataAfterDelete
    //   }
    // });

    managementTodoDispacth({
      type: 'delete_projects',
      payload: { id }
    })
  }

  const contextValue = {
    todos: managementTodoState.projects,
    tasks: managementTodoState.tasks,
    selectedProjectId: managementTodoState.selectedProjectId,
    onAddTaskToProjects: handleAddTaskToProjects,
    onDeleteTaskToProjects: handleDeleteTaskToProjects,
    onSelectedProject: handleSelectedProject,
    onStartAddProject: handleStartAddProject,
    onCancelAddProject: handleCancelAddProject,
    onDeleteProject: handleDeleteProject,
    onAddProject: handleAddProject
  }

  return (
    <ManagementTodoStore.Provider value={contextValue}>
      {children}
    </ManagementTodoStore.Provider>
  )

}