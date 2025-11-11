import React, { useEffect, useState } from 'react';
import axios from 'axios';

function App(){
  const [posts, setPosts] = useState([]);
  useEffect(()=> {
    axios.get('http://localhost:5000/api/posts')
      .then(res => setPosts(res.data))
      .catch(()=> {});
  },[]);
  return (
    <div style={{maxWidth:800, margin:'0 auto', padding:20}}>
      <h1>Simple MERN Blog</h1>
      <p>This is a starting scaffold. Login/register and create posts via API.</p>
      <section>
        {posts.map(p => (
          <article key={p._id} style={{border:'1px solid #ddd', padding:12, marginBottom:12}}>
            <h3>{p.title}</h3>
            <div>by {p.author?.name || 'Unknown'}</div>
            <p>{p.body.substring(0,200)}{p.body.length>200?'...':''}</p>
          </article>
        ))}
      </section>
    </div>
  );
}

export default App;
