import { createSlice } from "@reduxjs/toolkit";

const taskSlice = createSlice({
  name: "taskList",
  initialState: {
    items: []
  },
  reducers: {
    addNewTask: (state, action) => {
      const newTask = {
        id: new Date().getTime(),
        title: action.payload
      };
      state.items.push(newTask);
    },
    removeTask: (state, action) => {
      state.items = state.items.filter(
        (task) => task.id !== action.payload
      );
    }
  }
});

export const { addNewTask, removeTask } = taskSlice.actions;
export default taskSlice.reducer;
