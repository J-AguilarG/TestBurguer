from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
import uuid
import os

# Configuración de la App
app = Flask(__name__)
basedir = os.path.abspath(os.path.dirname(__file__))
# Usamos SQLite (creará un archivo burgers.db)
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + os.path.join(basedir, 'burgers.db')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)

# --- MODELO (La Entidad Burger) ---
class Burger(db.Model):
    id = db.Column(db.String(36), primary_key=True, default=lambda: str(uuid.uuid4()))
    nom = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String(255), nullable=True)
    prix = db.Column(db.Float, nullable=False)
    disponible = db.Column(db.Boolean, default=True)

    def to_dict(self):
        """Convierte el objeto a diccionario para JSON"""
        return {
            "id": self.id,
            "nom": self.nom,
            "description": self.description,
            "prix": self.prix,
            "disponible": self.disponible
        }

# --- ENDPOINTS (Rutas) ---

# 1. Crear un burger (POST)
@app.route('/burgers', methods=['POST'])
def create_burger():
    data = request.get_json()
    try:
        new_burger = Burger(
            nom=data['nom'],
            description=data.get('description', ''),
            prix=data['prix'],
            disponible=data.get('disponible', True)
        )
        db.session.add(new_burger)
        db.session.commit()
        return jsonify(new_burger.to_dict()), 201
    except Exception as e:
        return jsonify({"error": "Datos inválidos"}), 400

# 2. Listar burgers (GET)
@app.route('/burgers', methods=['GET'])
def get_burgers():
    burgers = Burger.query.all()
    return jsonify([b.to_dict() for b in burgers]), 200

# 3. Obtener un burger por ID (GET)
@app.route('/burgers/<id>', methods=['GET'])
def get_burger(id):
    burger = Burger.query.get(id)
    if not burger:
        return jsonify({"error": "Burger no encontrada"}), 404
    return jsonify(burger.to_dict()), 200

# 4. Actualizar burger (PUT)
@app.route('/burgers/<id>', methods=['PUT'])
def update_burger(id):
    burger = Burger.query.get(id)
    if not burger:
        return jsonify({"error": "Burger no encontrada"}), 404
    
    data = request.get_json()
    burger.nom = data.get('nom', burger.nom)
    burger.description = data.get('description', burger.description)
    burger.prix = data.get('prix', burger.prix)
    burger.disponible = data.get('disponible', burger.disponible)
    
    db.session.commit()
    return jsonify(burger.to_dict()), 200

# 5. Borrar burger (DELETE)
@app.route('/burgers/<id>', methods=['DELETE'])
def delete_burger(id):
    burger = Burger.query.get(id)
    if not burger:
        return jsonify({"error": "Burger no encontrada"}), 404
    
    db.session.delete(burger)
    db.session.commit()
    return jsonify({"message": "Burger eliminada"}), 200

# Inicializar la base de datos al arrancar
if __name__ == '__main__':
    with app.app_context():
        db.create_all()
    app.run(debug=True, port=5000)    