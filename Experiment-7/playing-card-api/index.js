
// Import express
const express = require('express');
const app = express();

// Middleware to parse JSON
app.use(express.json());

// In-memory array to store playing cards
let cards = [
  { id: 1, suit: 'Hearts', rank: 'Ace' },
  { id: 2, suit: 'Spades', rank: 'King' }
];

// ✅ 1. GET — Retrieve all cards
app.get('/cards', (req, res) => {
  res.status(200).json(cards);
});

// ✅ 2. GET — Retrieve a single card by ID
app.get('/cards/:id', (req, res) => {
  const card = cards.find(c => c.id === parseInt(req.params.id));
  if (!card) {
    return res.status(404).json({ message: 'Card not found' });
  }
  res.status(200).json(card);
});

// ✅ 3. POST — Create a new card
app.post('/cards', (req, res) => {
  const { suit, rank } = req.body;

  // Validation
  if (!suit || !rank) {
    return res.status(400).json({ message: 'Suit and Rank are required' });
  }

  const newCard = {
    id: cards.length + 1,
    suit,
    rank
  };

  cards.push(newCard);
  res.status(201).json(newCard);
});

// ✅ 4. PUT — Update an existing card
app.put('/cards/:id', (req, res) => {
  const card = cards.find(c => c.id === parseInt(req.params.id));
  if (!card) {
    return res.status(404).json({ message: 'Card not found' });
  }

  const { suit, rank } = req.body;
  if (!suit || !rank) {
    return res.status(400).json({ message: 'Suit and Rank are required' });
  }

  card.suit = suit;
  card.rank = rank;

  res.status(200).json(card);
});

// ✅ 5. DELETE — Remove a card
app.delete('/cards/:id', (req, res) => {
  const cardIndex = cards.findIndex(c => c.id === parseInt(req.params.id));
  if (cardIndex === -1) {
    return res.status(404).json({ message: 'Card not found' });
  }

  const deletedCard = cards.splice(cardIndex, 1);
  res.status(200).json({ message: 'Card deleted', deletedCard });
});

// Server listening
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
