import React from 'react'
import { UserProfile } from './UserProfile'

export function Navbar({ username }) {
  return (
    <div>
      <UserProfile username={username} />
    </div>
  )
}
