import { useContext } from "react";
import { ShoppingCartContext } from "../store/shopping-cart-context";
import ShoppingItem from "./shopping-item";
import ButtonShowCart from "./button-show-cart";
export default function Shopping() {
  const { shoppingItems, cartItems } = useContext(ShoppingCartContext);
  const size = cartItems.length;
  return (
    <div className="container mx-auto mt-24">
      <ButtonShowCart size={size} />
      <section className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-5 w-full">
        {shoppingItems.map((item, index) => (
          <ShoppingItem key={index} item={item} />
        ))}
      </section>
    </div>
  );
}