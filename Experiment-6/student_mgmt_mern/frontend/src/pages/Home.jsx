import React, { useEffect, useState } from 'react'
import API from '../api'
import { Link } from 'react-router-dom'

export default function Home(){
  const [students, setStudents] = useState([]);

  useEffect(()=> {
    fetchStudents();
  }, []);

  async function fetchStudents(){
    try{
      const res = await API.get('/students');
      setStudents(res.data);
    }catch(err){
      console.error(err);
    }
  }

  async function handleDelete(id){
    if(!confirm('Delete student?')) return;
    try{
      await API.delete('/students/' + id);
      setStudents(s => s.filter(x => x._id !== id));
    }catch(err){ console.error(err) }
  }

  return (
    <div>
      <h2>All Students</h2>
      {students.length===0 ? <p>No students yet. <Link to="/add">Add one</Link></p> :
        <table className="students">
          <thead><tr><th>Name</th><th>Email</th><th>Age</th><th>Course</th><th>Actions</th></tr></thead>
          <tbody>
            {students.map(s => (
              <tr key={s._id}>
                <td><Link to={'/view/'+s._id}>{s.firstName} {s.lastName}</Link></td>
                <td>{s.email}</td>
                <td>{s.age||'-'}</td>
                <td>{s.course||'-'}</td>
                <td>
                  <Link to={'/edit/'+s._id}>Edit</Link> | <button onClick={()=>handleDelete(s._id)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      }
    </div>
  )
}
