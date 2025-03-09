package com.voting.votingapp.services;

import com.voting.votingapp.model.OptionVote;
import com.voting.votingapp.model.Poll;
import com.voting.votingapp.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    public Poll createPoll(Poll poll){
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);
    }

    public void vote(Long pollId, int optionsIndex) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));
        List<OptionVote> options = poll.getOptions();
        if(optionsIndex <0 || optionsIndex>= options.size()){
            throw new IllegalArgumentException("Invalid option index");
        }
        OptionVote selectedOption = options.get(optionsIndex);
        selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);
        pollRepository.save(poll);
    }


}
