package ml.project4.river;

import burlap.oomdp.core.objects.ObjectInstance;
import burlap.oomdp.core.states.State;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Adapter for {@link State} interface.
 */
class StateAdapter implements State {
    @Override
    public State copy() {
        return null;
    }

    @Override
    public State addObject(ObjectInstance objectInstance) {
        return null;
    }

    @Override
    public State addAllObjects(Collection<ObjectInstance> collection) {
        return null;
    }

    @Override
    public State removeObject(String s) {
        return null;
    }

    @Override
    public <T> State setObjectsValue(String s, String s1, T t) {
        return null;
    }

    @Override
    public State removeObject(ObjectInstance objectInstance) {
        return null;
    }

    @Override
    public State removeAllObjects(Collection<ObjectInstance> collection) {
        return null;
    }

    @Override
    public State renameObject(String s, String s1) {
        return null;
    }

    @Override
    public State renameObject(ObjectInstance objectInstance, String s) {
        return null;
    }

    @Override
    public Map<String, String> getObjectMatchingTo(State state, boolean b) {
        return null;
    }

    @Override
    public int numTotalObjects() {
        return 0;
    }

    @Override
    public ObjectInstance getObject(String s) {
        return null;
    }

    @Override
    public List<ObjectInstance> getAllObjects() {
        return null;
    }

    @Override
    public List<ObjectInstance> getObjectsOfClass(String s) {
        return null;
    }

    @Override
    public ObjectInstance getFirstObjectOfClass(String s) {
        return null;
    }

    @Override
    public Set<String> getObjectClassesPresent() {
        return null;
    }

    @Override
    public List<List<ObjectInstance>> getAllObjectsByClass() {
        return null;
    }

    @Override
    public String getCompleteStateDescription() {
        return null;
    }

    @Override
    public Map<String, List<String>> getAllUnsetAttributes() {
        return null;
    }

    @Override
    public String getCompleteStateDescriptionWithUnsetAttributesAsNull() {
        return null;
    }

    @Override
    public List<List<String>> getPossibleBindingsGivenParamOrderGroups(String[] strings, String[] strings1) {
        return null;
    }
}
