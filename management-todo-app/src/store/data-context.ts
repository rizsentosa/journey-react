import type { TTodo, TTaskTodo, TSelectedProjectId } from '@/types/todo.type';
import { createContext } from "react";


export const ManagementTodoStore = createContext<{
  todos: TTodo[];
  tasks: TTaskTodo[];
  selectedProjectId: TSelectedProjectId;
  onAddTaskToProjects: (input: string) => void;
  onDeleteTaskToProjects: (id: string) => void;
  onSelectedProject: (id: string) => void;
  onStartAddProject: () => void;
  onCancelAddProject: () => void;
  onDeleteProject: (id: string) => void;
  onAddProject: (projectData: { title: string, description: string, createdAt: string }) => void;
}>({
  todos: [],
  tasks: [],
  selectedProjectId: null,
  onAddTaskToProjects: (input: string) => { },
  onDeleteTaskToProjects: (id: string) => { },
  onSelectedProject: (id: string) => { },
  onStartAddProject: () => { },
  onCancelAddProject: () => { },
  onDeleteProject: (id: string) => { },
  onAddProject: (projectData: { title: string, description: string, createdAt: string }) => { }
});