import { useEffect, useRef } from "react";
import { createPortal } from "react-dom";
type TProps = {
  children: React.ReactNode;
  open: boolean;
  onClose: () => void
}

const Modal = function Modal({ children, open, onClose }: TProps) {
  const dialogRef = useRef<HTMLDialogElement>(null);
  useEffect(() => {
    if (open) {
      dialogRef.current?.showModal();
    } else {
      dialogRef.current?.close();
    }
  }, [open])
  return createPortal(
    <>
      <dialog ref={dialogRef} className="m-auto w-full max-w-lg backdrop:bg-stone-900/90 p-4 rounded-md shadow-md bg-slate-200" onClose={onClose}>
        {open ? children : null}
      </dialog>
    </>,
    document.getElementById("modal-id") as HTMLElement);
};
export default Modal;