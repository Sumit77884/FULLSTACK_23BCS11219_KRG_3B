import { createSlice } from "@reduxjs/toolkit";

const saved = localStorage.getItem("cart");
const initialState = saved ? JSON.parse(saved) : { items: [], total: 0 };

const persist = (state) => localStorage.setItem("cart", JSON.stringify(state));

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addItem: (state, action) => {
      const item = action.payload;
      const existing = state.items.find(i => i.id === item.id);
      if (existing) {
        existing.quantity += 1;
      } else {
        state.items.push({ ...item, quantity: 1 });
      }
      state.total = state.items.reduce((s, it) => s + it.price * it.quantity, 0);
      persist(state);
    },
    removeItem: (state, action) => {
      const id = action.payload;
      state.items = state.items.filter(i => i.id !== id);
      state.total = state.items.reduce((s, it) => s + it.price * it.quantity, 0);
      persist(state);
    },
    updateQuantity: (state, action) => {
      const { id, quantity } = action.payload;
      const item = state.items.find(i => i.id === id);
      if (item) {
        item.quantity = quantity;
      }
      state.total = state.items.reduce((s, it) => s + it.price * it.quantity, 0);
      persist(state);
    },
    clearCart: (state) => {
      state.items = [];
      state.total = 0;
      persist(state);
    }
  }
});

export const { addItem, removeItem, updateQuantity, clearCart } = cartSlice.actions;
export default cartSlice.reducer;