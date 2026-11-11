import { useContext, useRef } from "react";
import Input from "./Input";
import Modal from "./Modal";
import { ManagementTodoStore } from "@/store/data-context";
export default function NewProject() {

  const { onAddProject, onCancelAddProject } = useContext(ManagementTodoStore);

  const modal = useRef<{ open: () => void }>(null);

  const titleRef = useRef<HTMLInputElement>(null);
  const descriptionRef = useRef<HTMLTextAreaElement>(null);
  const dueDateRef = useRef<HTMLInputElement>(null);

  const handleSave = () => {

    const enteredTitle = titleRef.current?.value ?? "";
    const enteredDescription = descriptionRef.current?.value ?? "";
    const enteredDuedate = dueDateRef.current?.value ?? "";

    if (
      enteredTitle.trim().length === 0 ||
      enteredDescription.trim().length === 0 ||
      enteredDuedate.trim().length === 0
    ) {
      modal.current?.open();
      return;
    }

    // valdation later
    onAddProject({ title: enteredTitle, description: enteredDescription, createdAt: enteredDuedate });

  }

  return (
    <>
      <Modal ref={modal} buttonCaption="oke">
        <h2 className="text-xl font-bold text-stone-500 my-4">invalid input..</h2>
        <p className="text-stone-600 mb-4">upss....looks like you forgot to enter a value,</p>
        <p className="text-stone-600 mb-4">please make sure you provide a valid value for every input field.</p>
      </Modal>
      <div className="w-140 mt-16">
        <menu className="flex items-center justify-end gap-4 my-4">
          <li><button onClick={onCancelAddProject} className="text-stone-800 hover:text-stone-950">cancel</button></li>
          <li><button className="px-6 py-2 rounded-md bg-stone-800 text-stone-50 hover:bg-stone-950" onClick={handleSave}>save</button></li>
        </menu>
        <div>
          <Input ref={titleRef} label="title" textarea={false} />
          <Input ref={descriptionRef} label="description" textarea={true} />
          <Input ref={dueDateRef} label="due date" type="date" textarea={false} />
        </div>
      </div>
    </>
  );
}