const admin = require('firebase-admin');
const fs = require('fs');
const serviceAccount = require('../config/serviceAccountKey.json'); // Asegúrate de proporcionar la ruta correcta a tu archivo serviceAccount.json

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

const exportData = async () => {

  // Exportar colección 'questions'
  const questionsCollectionRef = db.collection('questions'); //Especificar la collecion a imporat
  const questionsSnapshot = await questionsCollectionRef.get();
  
  let questionsData = {};
  questionsSnapshot.forEach(doc => {
    questionsData[doc.id] = doc.data();
  });

  // Guarda los datos en un archivo JSON
  fs.writeFileSync('questions.json', JSON.stringify(data, null, 2)); //Especificar el archivo que va a modificar
  console.log('Data exported successfully.');
};

exportData().catch(console.error);
