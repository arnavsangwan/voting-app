package com.votingapp.voting_app.service;

import com.votingapp.voting_app.model.OptionVote;
import com.votingapp.voting_app.model.Poll;
import com.votingapp.voting_app.repositories.PollRepository;
import com.votingapp.voting_app.request.Vote;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {
  final  private PollRepository pollRepository;

    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

    public  List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }
    public void vote(Long pollId,int optionIndex){
        // get poll from db
        Poll poll = pollRepository.findById(pollId).orElseThrow(()->
                new RuntimeException("Poll not found"));

        // get All  option
            List<OptionVote> options = poll.getOptions();


        // if index for vote is not valid , throw error
        if(optionIndex<0 || optionIndex >= options.size()){
            throw new IllegalArgumentException("Invalid option index");
        }
        // get selected option
        OptionVote selectedOption = options.get(optionIndex);
        // Increment vote for selected option
        selectedOption.setVoteCount(selectedOption.getVoteCount()+1);
        // save incremental option into the database
        pollRepository.save(poll);
    }

}
