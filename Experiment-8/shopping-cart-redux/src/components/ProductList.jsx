import { useDispatch } from "react-redux";
import { addItem } from "../features/cart/cartSlice";

const products = [
  { id: 1, name: "Apple", price: 100 },
  { id: 2, name: "Banana", price: 50 },
  { id: 3, name: "Orange", price: 80 },
];

export default function ProductList() {
  const dispatch = useDispatch();

  return (
    <div>
      <h2>Products</h2>
      {products.map(p => (
        <div key={p.id} style={{ marginBottom: 10, background: "#fff", padding: 10, borderRadius: 6 }}>
          <strong>{p.name}</strong> — ₹{p.price}
          <div style={{ marginTop: 6 }}>
            <button onClick={() => dispatch(addItem(p))}>Add to cart</button>
          </div>
        </div>
      ))}
    </div>
  );
}