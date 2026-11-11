import noProjectImg from "@/assets/no-projects.png";
import Button from "./Button";
import { useContext } from "react";
import { ManagementTodoStore } from "@/store/data-context";


export default function NoProjectSelected() {
  const { onStartAddProject } = useContext(ManagementTodoStore);
  return (
    <div className="mt-24 text-center w-2/3">
      <img src={noProjectImg} alt="no-project-selected" className="w-16 h-16 object-contain mx-auto" />
      <h2 className="text-xl font-bold text-stone-500 my-4">no project selected</h2>
      <p className="text-stone-400 mb-4">select a project or get started with a new one</p>
      <p className="mt-8">
        <Button onClick={onStartAddProject}>create new project</Button>
      </p>
    </div>
  );
}