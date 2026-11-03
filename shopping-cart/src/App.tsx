import Shopping from "./components/shopping";
import ShoppingCartStore from "./store/shopping-cart-store";

export default function App() {
  return (
    <ShoppingCartStore>
      <Shopping />
    </ShoppingCartStore>
  );
}