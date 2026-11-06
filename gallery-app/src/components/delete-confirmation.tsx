import { useEffect } from "react";
import ProgressBar from "./progress-bra";

type TProps = {
  onConfirm: () => void;
  onCancel: () => void;
}

const TIMER = 3000;

export default function DeleteConfirmation({ onCancel, onConfirm }: TProps) {

  useEffect(() => {
    const timeout = setTimeout(() => {
      onConfirm();
    }, TIMER);
    return () => clearTimeout(timeout);
  }, [onConfirm]);

  return <div className="p-4">
    <h2 className="text-xl font-bold">are you sure?</h2>
    <p className="mt-1 text-sm text-gray-500">do you really want to remove this place</p>
    <div className="flex justify-end gap-4 mt-4">
      <button onClick={onCancel} className="rounded-lg px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 active:scale-95 transition-all focus:outline-none focus:ring-2 focus:ring-gray-300">no</button>
      <button onClick={onConfirm} className="rounded-lg bg-red-600 px-4 py-2 text-sm font-semibold text-white shadow-sm hover:bg-red-700 active:scale-95 transition-all focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-offset-2">yes</button>
    </div>
    <ProgressBar timer={TIMER} />
  </div>
}