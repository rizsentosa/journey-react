import type { TTaskTodo, TTodo } from "@/types/todo.type";
import Tasks from "./Tasks";

type TSelectedProjectProps = {
  project: TTodo,
  onDeleteSelectedProject: (id: string) => void,
  onAddTask: (input: string) => void,
  onDeleteTask: (id: string) => void,
  tasks: TTaskTodo[]
}

export default function SelectedProject({ project, onDeleteSelectedProject, onAddTask, onDeleteTask, tasks }: TSelectedProjectProps) {
  const formattedDate = new Date(project.createdAt).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });
  return <div className="w-140 mt-16">
    <header className="pb-4 mb-4 border-b-2 border-stone-300">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-stone-600 mb-2">{project.title}</h1>
        <button className="text-stone-600 hover:text-stone-950" onClick={() => onDeleteSelectedProject(project.id)}>delete</button>
      </div>
      <p className="mb-4 text-stone-400">{formattedDate}</p>
      <p className="text-stone-600 whitespace-pre-wrap">{project.description}</p>
    </header>
    <Tasks onAdd={onAddTask} onDelete={onDeleteTask} tasks={tasks} />
  </div>
}