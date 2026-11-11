export type TTodo = {
  id: string;
  title: string;
  description: string;
  createdAt: string;
}

export type TTaskTodo = {
  id: string;
  title: string;
}

export type TSelectedProjectId = string | undefined | null;

export type ManagementTodoState = {
  projects: TTodo[],
  tasks: TTaskTodo[],
  selectedProjectId: TSelectedProjectId
}

export type ManagementTodoAction = { type: string, payload: {
  id?: string,
  title?: string,
  description?: string,
  createdAt?: string,
}}


