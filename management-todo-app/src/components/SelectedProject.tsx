import Tasks from "./Tasks";
import { useContext } from "react";
import { ManagementTodoStore } from "@/store/data-context";

export default function SelectedProject() {
  const { todos, selectedProjectId, onDeleteProject } = useContext(ManagementTodoStore);
  const data = todos.find(t => t.id === selectedProjectId)!;
  const formattedDate = new Date(data.createdAt).toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  });

  return <div className="w-140 mt-16">
    <header className="pb-4 mb-4 border-b-2 border-stone-300">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-stone-600 mb-2">{data.title}</h1>
        <button className="text-stone-600 hover:text-stone-950" onClick={() => onDeleteProject(data.id)}>delete</button>
      </div>
      <p className="mb-4 text-stone-400">{formattedDate}</p>
      <p className="text-stone-600 whitespace-pre-wrap">{data.description}</p>
    </header>
    <Tasks />
  </div>
}