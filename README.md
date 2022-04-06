# ProyectoVotacion
En este proyecto se propone un caso real en el que se debe votar Si, No o Abstención en un Parlamento
El cliente se conecta al servidor, se autentifica mediante una comparación del hash de las contraseñas
El servidor envía al cliente una llave pública con la que encriptar el valor del voto, este se envía al servidor
se realiza un desencriptado y el voto se recoge en una lista para enseñar el resultado de las votaciones posteriormente
En este proyecto se combina el envío de información mediante protocolo TCP y técnicas de criptografía.
