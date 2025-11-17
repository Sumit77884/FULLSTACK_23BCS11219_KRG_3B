import React from 'react'
import { Navbar } from './Navbar'

export function Header({ username }) {
  return (
    <div>
      <h2>My App</h2>
      <Navbar username={username} />
    </div>
  )
}
