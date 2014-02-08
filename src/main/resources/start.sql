CREATE OR REPLACE FUNCTION price_history()
  RETURNS trigger AS
$BODY$
BEGIN
  IF (TG_OP = 'DELETE') THEN
    INSERT INTO history (id, date, operation_type, price_id, pharmacy_id, drug_id, price)
      VALUES (nextval('history_sequence'), now(), 'D', OLD.id, OLD.pharmacy_id, OLD.drug_id, OLD.price);
  ELSEIF (TG_OP = 'UPDATE') THEN
    INSERT INTO history (id, date, operation_type, price_id, pharmacy_id, drug_id, price)
      VALUES (nextval('history_sequence'), now(), 'U', NEW.id, NEW.pharmacy_id, NEW.drug_id, NEW.price);
  ELSEIF (TG_OP = 'INSERT') THEN
    INSERT INTO history (id, date, operation_type, price_id, pharmacy_id, drug_id, price)
      VALUES (nextval('history_sequence'), now(), 'I', NEW.id, NEW.pharmacy_id, NEW.drug_id, NEW.price);
  END IF;
  RETURN NULL;
END;
$BODY$
LANGUAGE plpgsql;

CREATE TRIGGER price_insert_update_delete
  AFTER INSERT OR UPDATE OR DELETE
  ON price
  FOR EACH ROW
  EXECUTE PROCEDURE price_history();