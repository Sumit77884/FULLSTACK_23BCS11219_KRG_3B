import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { addNewTask, removeTask } from "./redux/taskSlice";

function App() {
  const [input, setInput] = useState("");
  const tasks = useSelector((state) => state.taskList.items);
  const dispatch = useDispatch();

  const handleSubmit = () => {
    if (input.length > 0) {
      dispatch(addNewTask(input));
      setInput("");
    }
  };

  return (
    <div style={{ width: "400px", margin: "40px auto", fontFamily: "Arial" }}>
      <h1>My Daily Tasks</h1>

      <div>
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder="Type a task..."
        />
        <button onClick={handleSubmit}>Add</button>
      </div>

      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            {task.title}
            <button
              style={{ marginLeft: "10px" }}
              onClick={() => dispatch(removeTask(task.id))}
            >
              Delete
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
