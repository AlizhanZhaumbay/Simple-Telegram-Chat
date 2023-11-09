const sendMessageToAll = "/app/messages"
const sendMessageToSpecific = "/app/messages/private"
const fetchPublicMessages = "/topic/messages"
const fetchPrivateMessages = "/user/topic/messages/private"


const sendBtn = document.querySelector("#send-message-btn")
const messageList = document.getElementById("message-list")
const refreshBtn = document.getElementById("refresh-btn")


const client = new StompJs.Client({
    brokerURL: "ws://localhost:8080/ws",
})


client.activate()


sendBtn.onclick = function () {
    const message = document.getElementById("message")
    const userList = document.getElementById("users")
    const selectedUser = userList.options[userList.selectedIndex]

    if (selectedUser.value === "Public") {
        sendPublicMessage(message)
    } else {
        sendPrivateMessage(message, selectedUser.value)
    }
}

client.onConnect = function (frame) {
    client.subscribe(fetchPublicMessages, (message) => {
        const messageContent = JSON.parse(message.body)
        const messageElement = document.createElement("li")
        messageElement.textContent = `${messageContent.from}(public):${messageContent.content}`
        messageList.appendChild(messageElement)
    })

    client.subscribe(fetchPrivateMessages, (message) => {
        const messageContent = JSON.parse(message.body)
        const messageElement = document.createElement("li")
        messageElement.textContent = `${messageContent.from}(private):${messageContent.content}`
        messageList.appendChild(messageElement)
    })
}

function sendPrivateMessage(message, recipient) {
    const jsonObj = {content: message.value, recipient: recipient}
    client.publish({
        destination: sendMessageToSpecific,
        body: JSON.stringify(jsonObj)
    })
    // fetch(`http://localhost:8080/send/${recipient}`, {
    //     method: "POST",
    //     body: JSON.stringify(jsonObj),
    //     headers: {
    //         "Content-type": "application/json; charset=UTF-8"
    //     }
    // })
}

function sendPublicMessage(message) {
    const jsonObj = {content: message.value}
    client.publish({
        destination: sendMessageToAll,
        body: JSON.stringify(jsonObj)
    })
}

refreshBtn.onclick = function () {
    fetch(`http://localhost:8080/users`, {
        method: "GET",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        }
    }).then(response => response.json())
        .then(response => {
            response.forEach(name => {
                const user = document.createElement("option")
                user.setAttribute("id", name)
                user.text = name
                const userList = document.getElementById("users");
                const optionExists = Array.from(userList.options).some(option => option.id === name);
                if(!optionExists){
                    userList.appendChild(user)
                }
            })

        })
}
