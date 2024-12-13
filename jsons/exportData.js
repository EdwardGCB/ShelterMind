const admin = require('firebase-admin');
const fs = require('fs');
const serviceAccount = require('../config/serviceAccountKey.json'); // Asegúrate de proporcionar la ruta correcta a tu archivo serviceAccount.json

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const exportData = async () => {
  // Exportar colección 'state'
  const stateCollectionRef = db.collection('state');
  const stateSnapshot = await stateCollectionRef.get();
  
  let stateData = {};
  stateSnapshot.forEach(doc => {
    stateData[doc.id] = doc.data();
  });

  // Exportar colección 'questions'
  const questionsCollectionRef = db.collection('questions');
  const questionsSnapshot = await questionsCollectionRef.get();
  
  let questionsData = {};
  questionsSnapshot.forEach(doc => {
    questionsData[doc.id] = doc.data();
  });

  // Combina ambas colecciones en un solo objeto
  const data = {
    state: stateData,
    questions: questionsData
  };

  // Guarda los datos en un archivo JSON
  fs.writeFileSync('questions.json', JSON.stringify(data, null, 2));
  console.log('Data exported successfully.');
};

exportData().catch(console.error);
