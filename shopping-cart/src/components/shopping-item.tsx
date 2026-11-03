import { useContext } from "react";
import type { ShoppingItem } from "../types/shopping-cart.type";
import { ShoppingCartContext } from "../store/shopping-cart-context";

type ShoppingItemProps = {
  item: ShoppingItem;
}

export default function ShoppingItem({ item }: ShoppingItemProps) {
  const { onAddToCart } = useContext(ShoppingCartContext);

  return (
    <div className="group flex flex-col justify-between bg-white rounded-xl border border-gray-100 shadow-sm hover:shadow-xl transition-all duration-300 overflow-hidden">
      <div className="relative aspect-square w-full overflow-hidden bg-gray-50">
        <img
          src={item.image_url}
          alt={item.name}
          className="h-full w-full object-cover transition-transform duration-500 group-hover:scale-105"
          loading="lazy"
        />
      </div>

      {/* Konten Kartu */}
      <div className="flex flex-1 flex-col justify-between p-5">
        <div>
          <h2 className="text-base font-medium text-gray-900 line-clamp-2 transition-colors group-hover:text-blue-600">
            {item.name}
          </h2>
        </div>

        <div className="mt-4 pt-4 border-t border-gray-100 flex items-center justify-between gap-2">
          <p className="text-xl font-bold text-gray-900">
            ${item.price.toFixed(2)}
          </p>

          <button
            onClick={() => onAddToCart(item)}
            className="inline-flex items-center justify-center rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-blue-700 active:scale-95 transition-all duration-150 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            Add to Cart
          </button>
        </div>
      </div>
    </div>
  );
}