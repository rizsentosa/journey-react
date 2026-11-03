import { useContext } from "react";
import { ShoppingCartContext } from "../store/shopping-cart-context";
import CartItem from "./cart-item";

export default function Cart() {
  const { cartItems } = useContext(ShoppingCartContext);

  // Menghitung total harga
  const subtotal = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0
  );

  // Tampilan ketika keranjang kosong
  if (cartItems.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center rounded-2xl border border-dashed border-gray-300 bg-gray-50/50 p-12 text-center">
        <div className="mb-4 flex h-16 w-16 items-center justify-center rounded-full bg-blue-50 text-blue-600">
          <svg className="h-8 w-8" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
            <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 10.5V6a3.75 3.75 0 10-7.5 0v4.5m11.356-1.993l1.263 12c.07.665-.45 1.243-1.119 1.243H4.25a1.125 1.125 0 01-1.12-1.243l1.264-12A1.125 1.125 0 015.513 7.5h12.974c.576 0 1.059.435 1.119 1.007zM8.625 10.5a.375.375 0 11-.75 0 .375.375 0 01.75 0zm7.5 0a.375.375 0 11-.75 0 .375.375 0 01.75 0z" />
          </svg>
        </div>
        <h2 className="text-xl font-bold text-gray-900">Keranjang Belanja Kosong</h2>
        <p className="mt-1 text-sm text-gray-500">
          Belum ada produk yang ditambahkan ke keranjang Anda.
        </p>
      </div>
    );
  }

  return (
    <div className="mx-auto max-w-4xl space-y-6">
      {/* Header Keranjang */}
      <div className="flex items-center justify-between border-b border-gray-200 pb-4">
        <h2 className="text-2xl font-bold text-gray-900">Shopping Cart</h2>
        <span className="text-sm font-medium text-gray-500">
          {cartItems.length} {cartItems.length === 1 ? 'Item' : 'Items'}
        </span>
      </div>

      {/* Daftar Item Keranjang */}
      <ul className="divide-y divide-gray-100">
        {cartItems.map((item) => (
          <li key={item.id} className="py-3">
            <CartItem item={item} />
          </li>
        ))}
      </ul>

      {/* Ringkasan Pembayaran & Tombol Checkout */}
      <div className="rounded-2xl border border-gray-100 bg-gray-50 p-6 space-y-4">
        <div className="flex items-center justify-between text-base font-medium text-gray-900">
          <p>Subtotal</p>
          <p className="text-xl font-bold text-blue-600">
            ${subtotal.toFixed(2)}
          </p>
        </div>
      </div>
    </div>
  );
}

