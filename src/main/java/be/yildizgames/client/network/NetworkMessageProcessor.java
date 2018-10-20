package be.yildizgames.client.network;

import be.yildizgames.common.mapping.MappingException;
import be.yildizgames.module.network.exceptions.InvalidNetworkMessage;
import be.yildizgames.module.network.protocol.MessageWrapper;

/**
 * Process the network messages, different implementations can be used depending of the state of the game(
 * not logged in, not initialized...)
 * 
 * @author Van den Borre Grégory
 *
 */
@FunctionalInterface
public interface NetworkMessageProcessor {

    /**
     * Process a network message against a given command.
     *
     * @param message
     *            Message itself.
     * @throws InvalidNetworkMessage
     *             If the message is not correctly understood or incomplete.
     */
    void process(MessageWrapper message) throws InvalidNetworkMessage, MappingException;
}
