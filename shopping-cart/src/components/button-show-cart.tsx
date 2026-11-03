import { useRef } from "react"
import Modal from "./modal";
import Cart from "./cart";

export default function ButtonShowCart({ size }: { size: number }) {

  const modal = useRef<{ open: () => void }>(null);

  const openModal = () => {
    modal.current?.open();
  }

  return <>
    <Modal ref={modal}>
      <Cart />
    </Modal>
    <div className="flex justify-end mb-12">
      <button className="relative inline-flex items-center justify-center rounded-lg bg-blue-600 px-5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-blue-700 active:scale-95 transition-all duration-150 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2" onClick={openModal}>
        <span>Cart Shopping</span>
        <span className="absolute -top-2 -right-2 flex h-5 w-5 items-center justify-center rounded-full bg-red-500 text-[11px] font-bold text-white shadow-sm ring-2 ring-white">
          {size}
        </span>
      </button>
    </div>
  </>
}