// promise-demo.js

function fetchData() {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve("Data fetched successfully");
      // reject("Something went wrong"); // (optional test)
    }, 2000);
  });
}

fetchData()
  .then((msg) => console.log("Success:", msg))
  .catch((err) => console.log("Error:", err));
