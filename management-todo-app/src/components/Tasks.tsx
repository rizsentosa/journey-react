import NewTask from "./NewTask";
import { useContext } from "react";
import { ManagementTodoStore } from "@/store/data-context";

export default function Tasks() {

  const { onDeleteTaskToProjects, onAddTaskToProjects, tasks } = useContext(ManagementTodoStore);

  return <section>
    <h2 className="text-2xl font-bold text-stone-700 mb-4">tasks</h2>
    <NewTask onAdd={onAddTaskToProjects} />
    {tasks.length === 0 && <p className="text-stone-200 my-4">this project does not have any tasks yet.</p>}
    {tasks.length > 0 && <ul className="p-4 mt-8 rounded-md bg-stone-100">
      {tasks.map((task) => (<li key={task.id} className="flex items-center justify-between my-4">
        <span>{task.title}</span>
        <button className="text-stone-700 hover:text-red-500" onClick={() => onDeleteTaskToProjects(task.id)}>clear</button>
      </li>))}
    </ul>}
  </section>
}