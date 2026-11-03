import { createContext } from "react";
import type { CartItem, ShoppingItem } from "../types/shopping-cart.type";

export const ShoppingCartContext = createContext<{
  shoppingItems: ShoppingItem[];
  cartItems: CartItem[];
  onAddToCart: (item: ShoppingItem) => void;
  onRemoveFromCart: (id: string) => void;
  onIncreseQuantity: (id: string) => void;
  onDecreaseQuantity: (id: string) => void;
  totalPrice: () => number,
  showCheckout: boolean,
}>({
  shoppingItems: [],
  cartItems: [],
  onAddToCart: () => { },
  onRemoveFromCart: () => { },
  onIncreseQuantity: () => { },
  onDecreaseQuantity: () => { },
  totalPrice: () => 0,
  showCheckout: false
});
