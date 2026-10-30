import { useState } from 'react'
import './App.css'
import NewProject from './components/NewProject'
import NoProjectSelected from './components/NoProjectSelected'
import ProjectSidebar from './components/ProjectSidebar'
import type { TSelectedProjectId, TTaskTodo, TTodo } from './types/todo.type'
import SelectedProject from './components/SelectedProject'

function generateTimeBasedId(): string {
  const timestamp = Date.now().toString(36); // Ubah timestamp ke base36 agar lebih pendek
  const randomPart = Math.random().toString(36).substring(2, 9); // 7 karakter random

  return `${timestamp}-${randomPart}`;
}


function App() {
  const [projectStates, setProjectStates] = useState<{
    selectedProjectId: TSelectedProjectId,
    projects: TTodo[],
    tasks: TTaskTodo[]
  }
  >({
    selectedProjectId: undefined,
    projects: [],
    tasks: []
  });

  const handleAddTaskToProjects = (input: string) => {
    setProjectStates((prevState) => {
      const newTask = {
        title: input,
        projectId: prevState.selectedProjectId,
        id: generateTimeBasedId()
      }
      return {
        ...prevState,
        tasks: [newTask, ...prevState.tasks]
      };
    })
  }


  const handleDeleteTaskToProjects = (id: string) => {
    const tasks = projectStates.tasks.filter(t => t.id != id);
    setProjectStates(prevState => {
      return {
        ...prevState,
        tasks
      }
    })
  }

  /**
   * update state selected project todo
   * @param id 
   */
  const handleSelectedProject = (id: string) => {
    setProjectStates((prevState) => {
      return {
        ...prevState,
        selectedProjectId: id
      }
    });
  }

  /**
   * initial select project id to null 
   */
  const handleStartAddProject = () => {
    setProjectStates((prevState) => {
      return {
        ...prevState,
        selectedProjectId: null
      }
    });
  }

  const handleCancelAddProject = () => {
    setProjectStates((prevState) => {
      return {
        ...prevState,
        selectedProjectId: undefined
      }
    });
  }

  /**
   * manipulate data to projectStates
   * @param projectData 
   */
  const handleAddProject = (projectData: { title: string, description: string, createdAt: string }) => {
    const newProject = {
      ...projectData,
      id: generateTimeBasedId()
    }

    setProjectStates((prevState) => {
      return {
        ...prevState,
        selectedProjectId: undefined,
        projects: [...prevState.projects, newProject]
      };
    })
  }

  const handleDeleteProject = (id: string) => {
    const existsDataAfterDelete = projectStates.projects.filter(p => p.id != id);
    setProjectStates((prevState) => {
      return {
        ...prevState,
        selectedProjectId: undefined,
        projects: existsDataAfterDelete
      }
    });
  }

  /**
   * render content if
   * null then render component NewProject
   * otherwise render component NoProjectSelecter
   */
  const selectedProject = projectStates.projects.find(p => p.id === projectStates.selectedProjectId)!;
  let content = <SelectedProject
    project={selectedProject}
    onDeleteSelectedProject={handleDeleteProject}
    onAddTask={handleAddTaskToProjects}
    onDeleteTask={handleDeleteTaskToProjects}
    tasks={projectStates.tasks} />;

  if (projectStates.selectedProjectId === null) {
    content = <NewProject onAdd={handleAddProject} onCancel={handleCancelAddProject} />
  } else if (projectStates.selectedProjectId === undefined) {
    content = <NoProjectSelected onStartAddProject={handleStartAddProject} />
  }

  return (
    <main className="h-screen my-8 flex gap-8 ">
      <ProjectSidebar onStartAddProject={handleStartAddProject} projects={projectStates.projects} onSelectProject={handleSelectedProject} selectedProjectId={projectStates.selectedProjectId as string} />
      {content}
    </main>
  )
}

export default App
