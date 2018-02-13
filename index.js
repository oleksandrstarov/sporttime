console.log('App started');


const SerialPort = require('serialport');

const ByteLength = SerialPort.parsers.ByteLength
const port = new SerialPort('COM4');
const parser = port.pipe(new ByteLength({length: 8}));
parser.on('data', console.log);
