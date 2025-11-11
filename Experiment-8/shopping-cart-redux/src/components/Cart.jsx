import { useSelector, useDispatch } from "react-redux";
import { removeItem, updateQuantity, clearCart } from "../features/cart/cartSlice";

export default function Cart() {
  const { items, total } = useSelector(s => s.cart);
  const dispatch = useDispatch();

  return (
    <div style={{ background: "#fff", padding: 12, borderRadius: 8 }}>
      <h2>Cart</h2>
      {items.length === 0 ? (
        <div>Your cart is empty</div>
      ) : (
        <>
          {items.map(item => (
            <div key={item.id} style={{ display: "flex", alignItems: "center", justifyContent: "space-between", marginBottom: 8 }}>
              <div>
                <div><strong>{item.name}</strong></div>
                <div>₹{item.price} × 
                  <input
                    type="number"
                    min="1"
                    value={item.quantity}
                    onChange={(e) => dispatch(updateQuantity({ id: item.id, quantity: Number(e.target.value) || 1 }))}
                    style={{ width: 60, marginLeft: 8 }}
                  />
                </div>
              </div>
              <div>
                <button onClick={() => dispatch(removeItem(item.id))}>Remove</button>
              </div>
            </div>
          ))}
          <hr />
          <div style={{ display: "flex", justifyContent: "space-between", marginTop: 8 }}>
            <strong>Total:</strong>
            <strong>₹{total}</strong>
          </div>
          <div style={{ marginTop: 10 }}>
            <button onClick={() => dispatch(clearCart())}>Clear Cart</button>
          </div>
        </>
      )}
    </div>
  );
}