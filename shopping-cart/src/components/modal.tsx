import { forwardRef, useContext, useImperativeHandle, useRef } from "react";
import { createPortal } from "react-dom";
import { ShoppingCartContext } from "../store/shopping-cart-context";

const Modal = forwardRef(({ children }: { children: React.ReactNode }, ref) => {

  const dialog = useRef<HTMLDialogElement>(null);
  const { showCheckout } = useContext(ShoppingCartContext);
  useImperativeHandle(ref, () => {
    return {
      open() {
        dialog.current?.showModal();
      }
    }
  });

  return createPortal(
    <dialog ref={dialog} className='m-auto w-full max-w-lg backdrop:bg-stone-900/90 p-4 rounded-md shadow-md '>
      {children}
      <div className="flex justify-between items-center mt-6">
        <button
          type="button"
          onClick={() => dialog.current?.close()}
          className="rounded-lg px-4 py-2 text-sm font-medium text-gray-600 hover:bg-gray-100 hover:text-gray-900 active:scale-95 transition-all focus:outline-none focus:ring-2 focus:ring-gray-300"
        >
          Close
        </button>

        {/* Tombol Checkout (Primary) */}
        {showCheckout && <button
          type="button"
          onClick={() => console.log('checkout')}
          className="inline-flex items-center justify-center rounded-lg bg-blue-600 px-5 py-2 text-sm font-semibold text-white shadow-sm hover:bg-blue-700 active:scale-95 transition-all focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
        >
          Checkout
        </button>}
      </div>
    </dialog>,
    document.getElementById("modal-root") as HTMLElement
  )

});

export default Modal;