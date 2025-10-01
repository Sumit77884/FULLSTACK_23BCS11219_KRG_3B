const svg = document.getElementById('canvas');
const colorPicker = document.getElementById('color');
let drawings = [];

// Handle mouse click to draw circles
svg.addEventListener('mousedown', (e) => {
  const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
  circle.setAttribute('cx', e.offsetX);
  circle.setAttribute('cy', e.offsetY);
  circle.setAttribute('r', '8');
  circle.setAttribute('fill', colorPicker.value); // dynamic color
  svg.appendChild(circle);
  
  drawings.push(circle); // add to undo stack
});

// Undo functionality
function undo() {
  const last = drawings.pop();
  if (last) {
    svg.removeChild(last);
  }
}
