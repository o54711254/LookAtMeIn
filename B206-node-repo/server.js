const express = require('express');

const socket = require('socket.io');

const PORT = 5000;

const {ExpressPeerServer} = require("peer")

const app = express()

const server = app.listen(PORT, ()=>{
    console.log(`Server is running on port ${PORT}`)

})

const peerServer = ExpressPeerServer(server,{debug:true})

app.use('/peerjs',peerServer)

peerServer.on('connection', (client) => {
    console.log('client connected', client.getId())
})

peerServer.on('disconnect', (client) => {
    console.log('client disconnected', client)
})

const io = socket(server,{
    cors:{
        origin:"*",
        method:["GET"]
    }
})

io.on('connection', (socket) => {
    socket.emit("connection")
    console.log('user', socket.id)

})   