// Sample product data
const products = [
  { id: 1, name: 'Smartphone', category: 'electronics' },
  { id: 2, name: 'Laptop', category: 'electronics' },
  { id: 3, name: 'T-shirt', category: 'fashion' },
  { id: 4, name: 'Jeans', category: 'fashion' },
  { id: 5, name: 'Novel', category: 'books' },
  { id: 6, name: 'Comics', category: 'books' }
];

// DOM Elements
const categoryFilter = document.getElementById('categoryFilter');
const productList = document.getElementById('productList');

function renderProducts(filteredProducts) {
  if (filteredProducts.length === 0) {
    productList.innerHTML = '<p class="no-results">No products found.</p>';
    return;
  }

  productList.innerHTML = filteredProducts.map(product => `
    <div class="product">
      <h4>${product.name}</h4>
      <p>Category: ${product.category}</p>
    </div>
  `).join('');
}


renderProducts(products);

categoryFilter.addEventListener('change', () => {
  const selectedCategory = categoryFilter.value;
  const filtered = selectedCategory === 'all'
    ? products
    : products.filter(product => product.category === selectedCategory);

  renderProducts(filtered);
});
