var mongoose = require('mongoose'),
	Schema = mongoose.Schema,
	model = mongoose.model

var calendarSchema  = new Schema({
	date: {type: Date, required: true},
	school: {type: mongoose.Schema.Types.ObjectId, ref : 'School', required: true},
	attendence: {type: Number, default: 0},
	uji: {MealType: {type: String, default: "Breakfast"}, Flour: {type: Number, default: 0}, Sugar: {type: Number, default: 0}, Water: {type: Number, default: 0}},
	githeri: {MealType: {type: String, default: "Lunch (Githeri)"},  Maize: {type: Number, default: 0}, Beans: {type: Number, default: 0}, Salt: {type: Number, default: 0}, Oil: {type: Number, default: 0}},
	rb: {MealType: {type: String, default: "Lunch (Rice and Beans)"}, Rice: {type: Number, default: 0}, Beans: {type: Number, default: 0}, Salt: {type: Number, default: 0}, Oil: {type: Number, default: 0}}
});

module.exports = mongoose.model('Calendar', calendarSchema);
