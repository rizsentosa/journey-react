export type CartItem = {
  id: string;
  name: string;
  price: number;
  quantity: number;
}

export type ShoppingItem = {
  id: string;
  name: string;
  image_url: string;
  price: number;
}

export type ShoppingCartState = {
  shoppingItems: ShoppingItem[];
  cartItems: CartItem[];
  showCheckout: boolean
}

export type ShoppingCartPayload = {
  type: string;
  payload: {
    id?: string;
    name?: string;
    image_url?: string;
    price?: number;
    quantity?: number;
  }
}