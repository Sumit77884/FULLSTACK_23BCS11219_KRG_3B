const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');
const Post = require('../models/Post');

// Create post
router.post('/', auth, async (req, res) => {
  const { title, body } = req.body;
  if(!title || !body) return res.status(400).json({ message: 'Missing fields' });
  const post = new Post({ title, body, author: req.user._id });
  await post.save();
  res.json(post);
});

// Read all
router.get('/', async (req, res) => {
  const posts = await Post.find().populate('author', 'name email').sort({ createdAt: -1 });
  res.json(posts);
});

// Read one
router.get('/:id', async (req, res) => {
  const post = await Post.findById(req.params.id).populate('author', 'name email').populate('comments.author','name');
  if(!post) return res.status(404).json({ message: 'Not found' });
  res.json(post);
});

// Update
router.put('/:id', auth, async (req, res) => {
  const post = await Post.findById(req.params.id);
  if(!post) return res.status(404).json({ message: 'Not found' });
  if(post.author.toString() !== req.user._id.toString()) return res.status(403).json({ message: 'Forbidden' });
  post.title = req.body.title || post.title;
  post.body = req.body.body || post.body;
  await post.save();
  res.json(post);
});

// Delete
router.delete('/:id', auth, async (req, res) => {
  const post = await Post.findById(req.params.id);
  if(!post) return res.status(404).json({ message: 'Not found' });
  if(post.author.toString() !== req.user._id.toString()) return res.status(403).json({ message: 'Forbidden' });
  await post.remove();
  res.json({ message: 'Deleted' });
});

// Add comment
router.post('/:id/comments', auth, async (req, res) => {
  const post = await Post.findById(req.params.id);
  if(!post) return res.status(404).json({ message: 'Not found' });
  const { text } = req.body;
  if(!text) return res.status(400).json({ message: 'Empty comment' });
  post.comments.push({ author: req.user._id, text });
  await post.save();
  await post.populate('comments.author', 'name');
  res.json(post);
});

module.exports = router;
