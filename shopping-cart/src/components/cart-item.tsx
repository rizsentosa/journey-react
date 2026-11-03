import { useContext } from "react";
import type { CartItem } from "../types/shopping-cart.type";
import { ShoppingCartContext } from "../store/shopping-cart-context";

export default function CartItem({ item }: { item: CartItem }) {
  const { onIncreseQuantity, onDecreaseQuantity } = useContext(ShoppingCartContext);
  return (
    <div className="flex items-center justify-between gap-4 rounded-xl border border-gray-100 bg-white p-4 shadow-sm hover:shadow-md transition-shadow mb-4">
      <div className="flex-1 min-w-0">
        <p className="truncate font-medium text-gray-900">{item.name}</p>
        <p className="text-sm font-semibold text-blue-600">
          ${item.price.toFixed(2)}
        </p>
      </div>

      <div className="flex items-center gap-2 rounded-lg border border-gray-200 bg-gray-50 p-1">
        <button
          className="flex h-7 w-7 items-center justify-center rounded-md bg-white text-gray-600 shadow-sm hover:bg-gray-100 active:scale-95 transition-all disabled:opacity-50"
          onClick={() => onDecreaseQuantity(item.id)}
        >
          -
        </button>

        <span className="w-8 text-center text-sm font-semibold text-gray-800">
          {item.quantity}
        </span>

        <button
          className="flex h-7 w-7 items-center justify-center rounded-md bg-white text-gray-600 shadow-sm hover:bg-gray-100 active:scale-95 transition-all"
          onClick={() => onIncreseQuantity(item.id)}
        >
          +
        </button>
      </div>
    </div>
  );
}