package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaTransactionListener {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public KafkaTransactionListener(
            UserRepository userRepository,
            TransactionRepository transactionRepository) {

        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(
            topics = "${general.kafka-topic}",
            groupId = "midas-group"
    )
    public void listen(Transaction transaction) {

        UserRecord sender =
        userRepository.findById(transaction.getSenderId());

        UserRecord recipient =
        userRepository.findById(transaction.getRecipientId());

        System.out.println("Sender: " +
        (sender != null ? sender.getName() : "null"));

        System.out.println("Recipient: " +
        (recipient != null ? recipient.getName() : "null"));


        if (sender == null || recipient == null) {
            return;
          }

        if (sender.getBalance() < transaction.getAmount()) {
            return;
          }

          sender.setBalance(
        sender.getBalance() - transaction.getAmount()
         );

        recipient.setBalance(
        recipient.getBalance() + transaction.getAmount()
        );

            userRepository.save(sender);
            userRepository.save(recipient);

            if ("waldorf".equals(sender.getName())) {
                System.out.println("WALDORF BALANCE = " + sender.getBalance());
              }

            if ("waldorf".equals(recipient.getName())) {
                System.out.println("WALDORF BALANCE = " + recipient.getBalance());
              }

             TransactionRecord record =
              new TransactionRecord(
                sender,
                recipient,
                transaction.getAmount()
            );

            transactionRepository.save(record);
           
    }
}