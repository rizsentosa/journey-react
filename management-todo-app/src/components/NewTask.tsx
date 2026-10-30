import { useState } from "react"

type TNewTaskProps = {
  onAdd: (input: string) => void
}

export default function NewTask({ onAdd }: TNewTaskProps) {
  const [enteredTask, setEnteredTask] = useState<string>('');
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEnteredTask(e.target.value);
  }
  const handleClick = () => {
    if (enteredTask.trim() === '') {
      return;
    }
    onAdd(enteredTask);
    setEnteredTask('');
  }
  return <div className="flex items-center gap-4">
    <input type="text" className="w-64 px-2 py-1 rounded-sm bg-stone-200" onChange={handleChange} value={enteredTask} />
    <button className="text-stone-700 hover:text-stone-950" onClick={handleClick}>add-task</button>
  </div>
}