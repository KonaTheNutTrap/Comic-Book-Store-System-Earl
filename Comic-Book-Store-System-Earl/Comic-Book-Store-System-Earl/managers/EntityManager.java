package managers;

import java.util.*;
//import java.io.*;
import utils.FileHandler;

/**
 * Abstract base class for managing entities in the Comic Book Store System.
 * 
 * This class implements the Template Method pattern to provide common
 * CRUD (Create, Read, Update, Delete) operations with file persistence.
 * Subclasses must implement abstract methods for entity-specific operations.
 * 
 * @param <T> The type of entity being managed
 * @author Comic Book Store System
 * @version 1.0
 */
public abstract class EntityManager<T> {
    /** List of entities currently loaded in memory */
    protected List<T> entities = new ArrayList<>();
    
    /** File path for data persistence */
    protected String filename;

    /**
     * Constructor for creating an EntityManager instance.
     * Initializes the manager with a data file and loads existing data.
     * 
     * @param filename The path to the data file for persistence
     */
    public EntityManager(String filename) {
        this.filename = filename;
        load(); // Load existing data from file on initialization
    }

    /**
     * Abstract method to parse a string into an entity object.
     * Subclasses must implement this to define entity-specific parsing logic.
     * 
     * @param line The string representation of the entity
     * @return The parsed entity object, or null if parsing fails
     */
    protected abstract T parse(String line);
    
    /**
     * Abstract method to serialize an entity to a string.
     * Subclasses must implement this to define entity-specific serialization.
     * 
     * @param entity The entity to serialize
     * @return String representation of the entity
     */
    protected abstract String serialize(T entity);
    
    /**
     * Abstract method to get the ID of an entity.
     * Subclasses must implement this to define how to extract the ID.
     * 
     * @param entity The entity to get the ID from
     * @return The entity's ID
     */
    protected abstract int getId(T entity);


    
    /**
     * Abstract method to update an entity's fields.
     * Subclasses must implement this to define entity-specific update logic.
     * 
     * @param entity The entity to update
     * @param sc Scanner for user input during update
     */
    protected abstract void updateEntity(T entity, Scanner sc);

    /**
     * Adds a new entity to the collection and persists to file.
     * 
     * @param entity The entity to add
     */
    public void add(T entity) {
        entities.add(entity);
        save(); // Persist changes to file
    }

    /**
     * Gets all entities in the collection.
     * 
     * @return List of all entities
     */
    public List<T> getAll() { return entities; }

    /**
     * Deletes an entity by its ID and persists changes to file.
     * 
     * @param id The ID of the entity to delete
     */
    public void delete(int id) {
        entities.removeIf(e -> getId(e) == id); // Remove entity with matching ID
        save(); // Persist changes to file
    }

    /**
     * Finds an entity by its ID.
     * 
     * @param id The ID to search for
     * @return The entity with the matching ID, or null if not found
     */
    public T findById(int id) {
        for (T e : entities)
            if (getId(e) == id)
                return e;
        return null; // Entity not found
    }



    /**
     * Updates an entity by its ID with new values from user input.
     * 
     * @param id The ID of the entity to update
     * @param sc Scanner for user input during update
     */
    public void update(int id, Scanner sc) {
        T entity = findById(id);
        if (entity != null) {
            updateEntity(entity, sc); // Delegate to subclass for entity-specific update
            save(); // Persist changes to file
            System.out.println("Updated successfully!");
        } else {
            System.out.println("Entity not found.");
        }
    }

    /**
     * Displays all entities using the provided display function.
     * Uses the Strategy pattern by accepting a function for display formatting.
     * 
     * @param displayFunc Function that converts an entity to a display string
     */
    public void displayAll(java.util.function.Function<T, String> displayFunc) {
        if (entities.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (T e : entities)
            System.out.println(displayFunc.apply(e)); // Apply display function to each entity
    }

    /**
     * Loads entities from the data file.
     * Reads each line from the file and parses it into entity objects.
     */
    public void load() {
        List<String> lines = FileHandler.readFile(filename);
        for (String line : lines) {
            T entity = parse(line); // Parse each line into an entity
            if (entity != null) entities.add(entity); // Add valid entities only
        }
    }

    /**
     * Saves all entities to the data file.
     * Serializes each entity and writes to the file.
     */
    public void save() {
        List<String> lines = new ArrayList<>();
        for (T entity : entities)
            lines.add(serialize(entity)); // Serialize each entity
        FileHandler.writeFile(filename, lines); // Write to file
    }

    /**
     * Generates the next available ID for a new entity.
     * Assumes IDs are sequential and based on the last entity's ID.
     * 
     * @return The next available ID
     */
    public int nextId() {
        if (entities.isEmpty()) return 1; // Start with ID 1 if no entities exist
        return getId(entities.get(entities.size() - 1)) + 1; // Increment last ID
    }
}
