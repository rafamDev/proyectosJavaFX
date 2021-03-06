CREATE TRIGGER mercancia_eliminada_trigger_AD AFTER DELETE ON mercancia
FOR EACH ROW 
INSERT INTO mercancia_eliminada(codigo,nombre,tipo,origen,destino,naturaleza,fecha_alta,fecha_modificacion,fecha_baja,observaciones)
VALUES(OLD.codigo,OLD.nombre,OLD.tipo,OLD.origen,OLD.destino,OLD.naturaleza,OLD.fecha_alta,OLD.fecha_modificacion,NOW(),OLD.observaciones);
