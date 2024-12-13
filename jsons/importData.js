const admin = require('firebase-admin');
const serviceAccount = require('../config/serviceAccountKey.json'); // Ruta correcta al archivo serviceAccount.json
const data = require('./states.json'); // Asegúrate de que tu archivo states.json está en la carpeta jsons

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const importData = async () => {
  const collectionRef = db.collection('state');
  
  for (const [id, state] of Object.entries(data.state)) {
    await collectionRef.doc(id).set(state);
  }
  
  console.log('Data imported successfully.');
};

importData().catch(console.error);
