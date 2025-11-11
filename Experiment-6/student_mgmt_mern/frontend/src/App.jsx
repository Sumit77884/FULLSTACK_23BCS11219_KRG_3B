import { Routes, Route, Link } from 'react-router-dom'
import Home from './pages/Home'
import AddStudent from './pages/AddStudent'
import EditStudent from './pages/EditStudent'
import ViewStudent from './pages/ViewStudent'

export default function App(){
  return (
    <div className="container">
      <header>
        <h1><Link to="/">Student Management (MERN)</Link></h1>
        <nav><Link to="/">Home</Link> | <Link to="/add">Add Student</Link></nav>
      </header>
      <main>
        <Routes>
          <Route path="/" element={<Home/>} />
          <Route path="/add" element={<AddStudent/>} />
          <Route path="/edit/:id" element={<EditStudent/>} />
          <Route path="/view/:id" element={<ViewStudent/>} />
        </Routes>
      </main>
    </div>
  )
}
