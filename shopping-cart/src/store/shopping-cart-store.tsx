import { useReducer } from "react";
import type { ShoppingCartPayload, ShoppingCartState } from "../types/shopping-cart.type";
import { ShoppingCartContext } from "./shopping-cart-context";
import { dummyShoppingItems } from "../dummy-data";


const INITIALVALUE_ONE = 1;

function shoppingCartReducer(state: ShoppingCartState, action: ShoppingCartPayload): ShoppingCartState {

  if (action.type === 'add_cart') {
    const objectPayload = {
      id: action.payload.id!,
      name: action.payload.name!,
      image_url: action.payload.image_url!,
      price: action.payload.price!,
      quantity: INITIALVALUE_ONE
    }

    const index = state.cartItems.findIndex(item => item.id === objectPayload.id);

    if (index === -1) {
      return {
        ...state,
        showCheckout: true,
        cartItems: [...state.cartItems, objectPayload]
      }
    }

    const newCartItems = state.cartItems.map(item => {
      if (item.id === objectPayload.id) {
        return {
          ...item,
          quantity: item.quantity + INITIALVALUE_ONE
        }
      }
      return item;
    });

    return {
      ...state,
      cartItems: newCartItems
    }
  }

  if (action.type === 'remove_cart') {
    const cartId = action.payload.id!
    const newCartItems = state.cartItems.filter(item => item.id !== cartId);
    return {
      ...state,
      showCheckout: false,
      cartItems: newCartItems
    }
  }

  if (action.type === 'increase_quantity') {
    const cartId = action.payload.id!
    const newCartItems = state.cartItems.map(item => {
      if (item.id === cartId) {
        return {
          ...item,
          quantity: item.quantity + INITIALVALUE_ONE
        }
      }
      return item;
    });
    return {
      ...state,
      cartItems: newCartItems
    }
  }

  if (action.type === 'decrease_quantity') {
    const cartId = action.payload.id!
    const newCartItems = state.cartItems.map(item => {
      if (item.id === cartId) {
        return {
          ...item,
          quantity: item.quantity - INITIALVALUE_ONE
        }
      }
      return item;
    }).filter(item => item.quantity > 0);

    return {
      ...state,
      showCheckout: newCartItems.length > 0,
      cartItems: newCartItems
    }
  }
  return state;
}

export default function ShoppingCartStore({ children }: { children: React.ReactNode }) {

  const [shoppingCartState, shoppingCartDispatch] = useReducer(shoppingCartReducer, {
    cartItems: [],
    shoppingItems: dummyShoppingItems,
    showCheckout: false
  });

  const handleIncreseQuantity = (id: string) => {
    shoppingCartDispatch({
      type: 'increase_quantity',
      payload: {
        id: id,
      }
    });
  }

  const handleDecreaseQuantity = (id: string) => {
    shoppingCartDispatch({
      type: 'decrease_quantity',
      payload: {
        id: id,
      }
    });
  }

  const handleOnRemoveToCart = (id: string) => {
    shoppingCartDispatch({
      type: 'remove_cart',
      payload: {
        id: id,
      }
    });
  }

  const handleOnAddToCart = (item: { id: string, name: string, image_url: string, price: number }) => {
    shoppingCartDispatch({
      type: 'add_cart',
      payload: {
        id: item.id,
        name: item.name,
        image_url: item.image_url,
        price: item.price
      }
    });
  }

  const handleTotalPrice = (): number => {
    const totalPrice = shoppingCartState.cartItems.reduce((total, item) => {
      return total + (item.price * item.quantity);
    }, 0);
    return totalPrice;
  }

  const ctxValue = {
    shoppingItems: shoppingCartState.shoppingItems,
    cartItems: shoppingCartState.cartItems,
    showCheckout: shoppingCartState.showCheckout,
    onAddToCart: handleOnAddToCart,
    onRemoveFromCart: handleOnRemoveToCart,
    onIncreseQuantity: handleIncreseQuantity,
    onDecreaseQuantity: handleDecreaseQuantity,
    totalPrice: handleTotalPrice,
  }

  return (
    <ShoppingCartContext.Provider value={ctxValue}>
      {children}
    </ShoppingCartContext.Provider>
  )
}