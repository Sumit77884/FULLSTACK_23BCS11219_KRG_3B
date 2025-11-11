import ProductList from "./components/ProductList";
import Cart from "./components/Cart";

export default function App() {
  return (
    <div style={{ padding: 20, fontFamily: "Arial, sans-serif" }}>
      <h1>🛒 Redux Shopping Cart (Minimal UI)</h1>
      <div style={{ display: "flex", gap: 40 }}>
        <div style={{ flex: 1 }}>
          <ProductList />
        </div>
        <div style={{ width: 360 }}>
          <Cart />
        </div>
      </div>
    </div>
  );
}