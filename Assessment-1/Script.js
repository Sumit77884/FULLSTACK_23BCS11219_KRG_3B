document.getElementById("signupForm").addEventListener("submit", function(e) {
  e.preventDefault();

  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const msg = document.getElementById("message");

  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (!name || !email || !password) {
    msg.style.color = "red";
    msg.textContent = "All fields are required!";
  } else if (!emailPattern.test(email)) {
    msg.style.color = "red";
    msg.textContent = "Enter a valid email!";
  } else {
    msg.style.color = "green";
    msg.textContent = "Registration successful!";
    document.getElementById("signupForm").reset();
  }
});

